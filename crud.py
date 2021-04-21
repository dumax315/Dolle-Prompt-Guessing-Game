from replit import db


import schemas

def insert(name: schemas.nameInsert):
    data=name.dict()  # Extract value from schema as dictionary
    name, message = data['name'], data['message']

    #insert in database
    db[name] = message
    
    #Read back inserted value
    #and make a schema object from it fastapi will auto create json response from this
    return schemas.nameOut(name=name,message= db[name]) 
    
def get(name: schemas.nameRead):
    data=name.dict()  # Extract value from schema as dictionary
    name = data['name'] #as per nameRead schema definition there is only name
    return schemas.nameOut(name=name,message= db[name])     

#To read when direct value is passedd
def get_direct(name: str):
    return schemas.nameOut(name=name,message= db[name]) 