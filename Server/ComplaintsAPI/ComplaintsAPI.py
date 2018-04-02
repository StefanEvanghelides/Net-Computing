from flask import Flask
from werkzeug.contrib.fixers import ProxyFix

from endpoints.api import API

app = Flask(__name__)
app.wsgi_app = ProxyFix(app.wsgi_app)

API.init_app(app)

if __name__ == '__main__':
    app.run(host="0.0.0.0", debug=True)
