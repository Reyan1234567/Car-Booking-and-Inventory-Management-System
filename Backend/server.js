import express from "express"
import {config} from "dotenv"
import connect from "./config/db.js"
import routes from "./routes/route.js"
const app=express()

app.use(express.json())
app.use(cors())
app.use(routes)
config()

connect()


const PORT=process.env.PORT
app.listen(PORT,()=>{
    console.log(`express running on port: ${PORT}`)
})