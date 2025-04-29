import jwt from "jsonwebtoken"
import {config} from "dotenv"

config()

const checkAccessToken=(req,res,next)=>{
    const {authorization}=req.headers
    if(!authorization){
        res.status(401).json({"error":"Unauthorized"})
    }
    console.log(`IN THE MIDDLEWARE:${authorization}`)

    const accessToken=authorization.split(" ")[1]
    if(!accessToken){
        res.status(401).json({"error":"Unauthorized"})
    }

    jwt.verify(accessToken, process.env.ACCESS_TOKEN,(decoded,err)=>{
        if(err){
            console.log(err)
            let errorMessage="Unauthorized - Invalid Token"
            if(err.name === 'TokenExpiredError'){
                errorMessage="Unauthorized - Token expired"
            }
            res.status(401).json({"error": errorMessage})
        }
        
        if(!decoded){
            console.log("Unauthorized: No decoded payload")
            res.json({error:"Unauthorized-No decoded payload"}).status(401)
        }
        next()
    } )
}

export default checkAccessToken