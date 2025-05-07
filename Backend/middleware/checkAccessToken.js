import jwt from "jsonwebtoken"
import {config} from "dotenv"

config()

const checkAccessToken=async(req,res,next)=>{
    const {authorization}=req.headers
    console.log(authorization)
    if(!authorization||!authorization.split(" ")[1]){
        return res.status(401).json({"error":"Unauthorized"})
    }
    console.log(`IN THE MIDDLEWARE:${authorization}`)

    const accessToken=authorization.split(" ")[1]
    console.log(`${accessToken} before check`)

    console.log("before verification")
    jwt.verify(accessToken, process.env.ACCESS_TOKEN,(err,decoded)=>{
        if(err){
            console.log(err)
            let errorMessage="Unauthorized - Invalid Token"
            if(err.name === 'TokenExpiredError'){
                errorMessage="Unauthorized - Token expired"
            }
            return res.status(401).send(errorMessage)
        }
        
        if(!decoded){
            console.log("Unauthorized: No decoded payload")
            return res.send("Unauthorized - No decoded payload").status(401)
        }
        req.user = decoded;

        next()
    } )
}

export default checkAccessToken