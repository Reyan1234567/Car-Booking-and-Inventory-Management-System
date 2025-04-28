import {Router} from "express"
import Cars from "../models/cars.js"
import Bookings from "../models/bookings.js"

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



router.post("/api/filteredCars", async (req, res) => {
    const { body } = req;
    console.log(body);
    console.log(body.startDate);

    try {
        if (!body.startDate || !body.endDate) {
            return res.status(400).send("Both startDate and endDate are required.");
        }

        const convertToDate = (stringDate) => {
            const [date, month, year] = stringDate.split("/");
            const newDate = new Date(year, month - 1, date);
            return newDate;
        };

        console.log(body.startDate);
        const newStartDate = convertToDate(body.startDate);
        const newEndDate = convertToDate(body.endDate);

        console.log(newStartDate);

        const CarIdsBookedAtThatTime = await Bookings.find({
            $or:[{startDate:{$gt:newEndDate}},
            {endDate:{$lt:newStartDate}}]
        }).distinct(carId);

        const filteredCars=await Cars.find({
            _id:{$nin:CarIdsBookedAtThatTime}
        })
        
        console.log(filteredCars);
        res.status(200).json(filteredCars); 
    } catch (err) {
        console.log(err);
        res.status(500).send(err.message); 
    }
});












export default router












// notAvailableOn:{
//     $not:{
//         $elemMatch:{
//             startDate:{$lte:newEndDate},
//             endDate:{$gte:newStartDate}
//         }
//     }
// }