import express from "express"
import {config} from "dotenv"
import cors from "cors"
import connect from "./config/db.js"
import routes from "./routes/route.js"
const app=express()

app.use(express.json())
app.use(cors({
    origin: 'http://10.0.2.2:4000',
    methods: ['GET', 'POST', 'PUT', 'DELETE'],
    allowedHeaders: ['Content-Type', 'Authorization']
}));app.use(routes)
config()

connect()


const PORT=process.env.PORT
app.listen(PORT,'0.0.0.0',()=>{
    console.log(`express running on port: ${PORT}`)
})