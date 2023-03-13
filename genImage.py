
import random
import openai
import os




def genImage():
  my_secret = os.environ['token']
  openai.api_key = my_secret

  nouns = ["cheese", "man", "woman", "sphere", "bird", "cat",   "dog", "airplane", "car", "vehicle", "frog", "cake", "food",   "wheel", "cube", "pyramid", "tree", "plant", "house", "building", "shrine", "temple", "liquid", "instrument", "lizard", "turtle", "astronaut", "scientist", "businessperson", "boardgame", "church", "school", "world", "number", "money", "book", "eye", "city", "town", "face", "sky", "apple", "banana", "fruit", "boat", "bus", "currency", "diamond", "mountain", "egg", "forest", "evinronment", "farmer", "ladder", "lake", "library", "machine", "robot", "math", "medicine", "money", "morning", "evening", "night", "music", "polluition", "salad", "clock", "train", "village", "writing", "language", "text", "fire", "rain"]
  adjectives = ["large", "massive", "sad", "happy", "angry", "blue", "green", "orange", "red", "yellow", "white", "black", "purple", "rainbow", "confused", "excited", "underwater", "outerspace", "scared", "abstract", "messy", "clean", "swirly", "old", "ancient", "new", "tiny", "round", "intelligent", "colorful", "monochrome", "dull", "extravagant", "glamorous", "long", "tall", "short", "smiling", "frowning", "jumping", "flying", "sparkly", "shining", "bright", "dark", "running", "wild", "calm", "geometric", "spherical", "wooden", "stony", "metallic", "royal", "thin", "wide", "simple", "chaotic", "complex"]
  style = ["minimalism", "vintage", "flat art", "3D illustration", "psychedelic", "Leonardo da Vinci", "Pablo Picasso", "Salvador Dali", "Vincent van Gogh", "Claude Monet", "bauhaus", "baroque", "pop art", "impressionism", "cubism", "low poly", "vaporwave"]

  nvars = random.sample(nouns,5)
  avars = random.sample(adjectives,5)

  usedWords = [nvars[0],nvars[1],avars[0],avars[1]]
  allWords = nvars + avars

  random.shuffle(allWords)
  #print(allWords)


  random.shuffle(usedWords)
  s = random.sample(style,1)
#print(usedWords)
#print(s)
  st = ' '.join(s)
  prompt = ' '.join(usedWords) +" in "+ st +" style"

  #print(prompt)



  response = openai.Image.create(
      prompt=prompt,
      model="image-alpha-001",
      size="1024x1024",
      response_format="url"
  )


  #print(response["data"][0]["url"])

  return(usedWords, allWords, response["data"][0]["url"])