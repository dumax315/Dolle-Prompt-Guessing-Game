import uvicorn
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from starlette.responses import RedirectResponse
from replit import db as users 
import schemas
import crud



from typing import Union, Optional

from fastapi import FastAPI, Path, Query, HTTPException, status
from pydantic import BaseModel

app = FastAPI()

class User(BaseModel):
    username :str
    points: int 

#class for when your class doesn't have all attributes

# class UpdateUser(BaseModel):
#     username :Optional[str] = None
#     points: Optional[int] = None 


@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.get("/about")
def about():
    return{"Data": "About"}


@app.get("/get-user-points")
def get_item(username: str):
    if username not in users:
        #return {"Error": "Username doesn't already exist"}
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="user not found")
    return users[username]

@app.get("/get-users")
def get_users():
    return users


# function for updating points, function for creating user, function for getting points, function for generating and outputting leaderboard

@app.get("/create-user/{username}")
def create_item(username:str, points: int):
    if username in users:
        return {"Error": "Username already exists."}
    #users[username] = {"username": user.username, "points": user.points}
    #users[username].points
    users[username]= points
    return users[username]

@app.get("/update-user/{username}")   #function to update user
def update_user(username:str, points: int):
    if username not in users:
        return {"Error": "Username doesn't already exist"}
    users[username] += points
    return users[username]


@app.delete("/delete-user")
def delete_user(username :str = Query(..., description = "delete the user with the username")):
    if username not in users:
        return {"Error": "User doesn't exist"}
    
    del users[username]

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)

