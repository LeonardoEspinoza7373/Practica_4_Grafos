from flask import Flask 

def create_app():
    app = Flask(__name__, instance_relative_config=False)
    
    app.secret_key = 'grafos'
    
    with app.app_context():
        from front.routes.route import router 
        app.register_blueprint(router) 
    return app
