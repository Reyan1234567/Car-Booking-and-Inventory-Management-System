import Cars from "../models/cars"
import {Router} from "express"

const router=Router()

//Get All cars
router.get("/cars",async (req,res)=>{
    const response=await Cars.findA()
    try{
        if(!response){
            return res.status(401).send("No Cars found")
        }
        res.status(200).send(response)
    }
    catch(err){
        console.log(err)
        res.status(401).send(err.message)
    }
})

//Get a specific car
router.get("cars/:id",async(req,res)=>{
    const {id}=req.params
    const response=await Cars.findById(id)
    try{
        if(!response){
            return res.status(401).send(`No car with id:${id}`)
        }
        res.status(200).send(response)
    }
    catch(err){
        console.log(err)
        res.status(401).send(err.message)
    }
})

router.put("cars/:id",async(req,res)=>{
    const {id}=req.params
    const {body}=req
    try{
        const updatedCar=await Cars.findByIdAndUpdate(id,body,{
            new:true,
            runValidators:true
        })
        if(!updatedCar){
            return res.status(401).send("can't find booking")
        }
        res.status(201).send(updatedCar)
    }
    catch(err){
        console.log(err)
        res.status(401).send(err.message)
    }
})











export default router