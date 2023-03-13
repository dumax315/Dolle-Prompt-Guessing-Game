import uvicorn
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from starlette.responses import RedirectResponse
from replit import db as users 
import schemas
import crud

import asyncio

from genImage import genImage


from typing import Union, Optional

from fastapi import FastAPI, Path, Query, HTTPException, status, BackgroundTasks
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


# need to make function for generating and outputting leaderboard
@app.get("/generate-leaderboard")
def generate_leaderboard():
  leaderboard = sorted(users.items(),key=lambda x:x[1], reverse = True)
  
  
  return(dict(leaderboard))
  
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

images = []


@app.get("/get-image")
def get_image(background_tasks: BackgroundTasks):
  #  return(usedWords, allWords, response["data"][0]["url"])
  background_tasks.add_task(gen_image)
  if(len(images)==0):
    print("number of images in stack: "+str(len(images)))
    result = genImage()
    print("new image loaded")
    ai_result = {
      "usedWords": result[0],
      "allWords": result[1],
      "url": result[2]
    }
    return images.append(ai_result)
  
    
  return images.pop()
  
async def gen_image():
  print("number of images in stack: "+str(len(images)))
  result = genImage()
  print("new image loaded")
  ai_result = {
    "usedWords": result[0],
    "allWords": result[1],
    "url": result[2]
  }
  images.append(ai_result)

async def createFirstImages():
    # Schedule three calls *concurrently*:
    L = await asyncio.gather(
        gen_image(),
      gen_image(),gen_image()
    )
    print(L)

# TODO: check for stale images on user log in
asyncio.run(createFirstImages())


@app.delete("/delete-user")
def delete_user(username :str = Query(..., description = "delete the user with the username")):
    if username not in users:
        return {"Error": "User doesn't exist"}
    
    del users[username]

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)


