from asyncio.log import logger
import os
import time
import subprocess
import json
from http.server import BaseHTTPRequestHandler, HTTPServer

def write_param(records_id, url):
    # 在 Python 代码中将值写入文件
    with open('/opt/playwright.txt', 'w') as f:
        f.write(f'RECORD_ID={records_id}\n')
        f.write(f'URL={url}\n')


def update_xsession_file(record_id, url):
    # 生成.xsession内容
    content = f'''
    playwright codegen --target python -o /opt/actions/record_{record_id}.py {url}
    '''

    # 写入或追加到.xsession文件
    xsession_path = os.path.expanduser("~/.xsession")
    with open(xsession_path, "w") as xsession_file:
        xsession_file.write(content)



class MyRequestHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        # 处理来自主服务器的GET请求
        # 例如，可以在这里启动VNC Server，并返回其序号
        command = "vncserver"
        process = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        time.sleep(3)

        output, error = process.communicate()
        # 提取VNC服务器的序号
        vncserver_number = None
        if process.returncode == 0:
            for line in output.decode().splitlines():
                if "New 'localhost" in line:
                    vncserver_number = line.split()[2].strip("'")

        # 返回响应给主服务器
        self.send_response(200)
        self.send_header('Content-type', 'text/html')
        self.end_headers()
        self.wfile.write(bytes(f"VNC服务器已启动，序号为：{vncserver_number}", "utf-8"))

    def do_POST(self):
        content_length = int(self.headers['Content-Length'])
        post_data = self.rfile.read(content_length).decode('utf-8')
        data = json.loads(post_data)

        response_data = {}
        status_code = 200

        vncserver_number = None  # 将 vncserver_number 的赋值提到这里

        if 'action' in data:
            action = data['action']
            if action == 'create_vnc':
    # 执行VNC指令并返回VNC服务器的序号ID
                if 'records_id' in data and 'url' in data:
                    try:
                        logger.info(f'records_id: {data["records_id"]}')
                        records_id = int(data['records_id'])
                        url = data['url']
                        print(f"print record id:{records_id}")
                        update_xsession_file(records_id, url)

                        # 等待一段时间，确保文件更新完成
                        time.sleep(1)

                        command = ["vncserver"]
                        process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
                        error, output = process.communicate()
                        time.sleep(3)
                        vncserver_number = None
                        if process.returncode == 0:
                            for line in output.decode().splitlines():
                                if "desktop" in line:
                                    vncserver_number = line.split()[-1].split(':')[1]
                                    break
                        if not vncserver_number:
                            status_code = 500
                            response_data['message'] = 'VNC服务器启动失败'
                    except Exception as e:
                        # 处理异常情况，例如超时或其他错误
                        print(f"An error occurred: {e}")
                        status_code = 500
                        response_data['message'] = '服务器发生错误'
                else:
                    status_code = 400
                    response_data['message'] = '缺少records_id/url参数'

                response_data['vncserver_number'] = vncserver_number

            elif action == 'stop_vnc':
                # 关闭VNC服务器
                if 'vnc_id' not in data:
                    status_code = 400
                    response_data['message'] = '缺少vnc_id参数'
                else:
                    command = ["vncserver", "-kill", ":" + str(data['vnc_id'])]
                    process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
                    error, output = process.communicate()
                    record_id = int(data['records_id'])
                    print(data['vnc_id'])
                    print(f"record id:{record_id}")
                    file_path = "/opt/actions/record_{}.py".format(record_id)
                    if os.path.exists(file_path):
                        with open(file_path, 'r') as f:
                            file_content = f.read()
                        response_data['file'] = file_content
                        os.remove(file_path)
                    else:
                        response_data['file'] = 'no acrion file fonud...'
            else:
                status_code = 400
                response_data['message'] = '未知的action字段值'
        else:
            status_code = 400
            response_data['message'] = '缺少action字段'

        response_data['status'] = status_code

        # 设置响应头
        self.send_response(status_code)
        self.send_header('Content-Type', 'application/json')
        self.end_headers()

        # 设置响应体
        response_body = json.dumps(response_data).encode('utf-8')
        self.wfile.write(response_body)

def main():
    # 启动HTTP服务器
    server_address = ('', 8000)
    httpd = HTTPServer(server_address, MyRequestHandler)
    print(f'Starting Python HTTP server on port {server_address[1]}...')
    httpd.serve_forever()

if __name__ == '__main__':
    main()