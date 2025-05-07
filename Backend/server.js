import express from "express"
import {config} from "dotenv"
import cors from "cors"
import connect from "./config/db.js"
import routes from "./routes/route.js"
const app=express()

config()
connect()

app.use(express.json())
app.use(cors());  // Allow all origins during development

// Serve static files before routes
app.use('/uploads', express.static('uploads'));

app.use(routes)

const PORT=process.env.PORT
app.listen(PORT,'0.0.0.0',()=>{
    console.log(`express running on port: ${PORT}`)
})