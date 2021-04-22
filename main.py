import uvicorn
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from starlette.responses import RedirectResponse

import schemas
import crud

#intialize web app / api
app = FastAPI()

# Allows cors for everyone **Ignore**
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
    allow_credentials=True,
)

# Redirects base url to docs goto /redoc for more fancy documentation 
@app.get("/")
def main():
    return RedirectResponse(url="/docs")

# POST request for Name Write
@app.post("/api/write", response_model=schemas.nameOut)
def post_name(dataIn: schemas.nameInsert):
    return crud.insert(dataIn)

# post request for Name Read
@app.post("/api/read", response_model=schemas.nameOut)
def read_name(dataIn: schemas.nameRead):
    return crud.get(dataIn)

# GET request for Name Read name is passed in url rather than json 
@app.get("/api/read/{name}", response_model=schemas.nameOut)
def get_name(name:str):
    return crud.get_direct(name)


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)