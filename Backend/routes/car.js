import {Router} from "express"
import Cars from "../models/cars.js"
import Bookings from "../models/bookings.js"
import checkAccessToken from "../middleware/checkAccessToken.js";
import carImage from "../models/CarImages.js";

const router=Router()


router.use(checkAccessToken)
//Get All cars
router.get("/cars",async (req,res)=>{
    const response=await Cars.find()
    const carsArray=[]
    response.forEach((respons)=>{
        if(respons.image!==null||respons.image!=undefined||respons.image==""){
            const imageUrl=carImage.findOne(respons.image)
            if(!imageUrl){
                respons.image=""
            }
            else{
                const carUrl=imageUrl.url
                respons.image=carUrl
            }
        }
        carsArray.push(respons)
    })
    try{
        if(!carsArray){
            return res.status(401).send("No Cars found")
        }
        res.status(200).send(carsArray)
    }
    catch(err){
        console.log(err)
        res.status(401).send(err.message)
    }
})

//Get a specific car
router.get("cars/:id",async(req,res)=>{
    const {id}=req.params
    const car=await Cars.findById(id)
    try{
        if(!response){
            return res.status(401).send(`No car found`)
        }
        const imageUrl=carImage.findOne(car.image)
            if(!imageUrl){
                car.image=""
            }
            else{
                const carUrl=imageUrl.url
                respons.image=carUrl
            }
        res.status(200).send(car)
    }
    catch(err){
        console.log(err)
        res.status(401).send(err.message)
    }
})

router.patch("cars/:id",async(req,res)=>{
    const {id}=req.params
    const {body}=req
    try{
        const updates={}
        
        for(const key in body){
            if(!body.hasOwnProperty(key)) {continue};
            if(!body[key]){
                continue
            }
            else{
                updates[key]=body[key]
            }
        }
        const updatedCar=await Cars.findByIdAndUpdate(id,updates,{
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
        res.status(401).json({"error": err.message})
    }
})

router.get("/total_cars",async (req,res)=>{
    try{
      const cars=await Cars.findAll()
      if(!cars||cars.length===0){
        res.status(404).send("No Cars found!")
      }
      else{
        res.status(200).send(cars.length)
      }
    }
    catch(e){
      res.status(404).send("Some car error")
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
            // const sdf=SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            // return sdf.parse(stringDate)
            const [date, month, year] = stringDate.split("/");
            const newDate = new Date(year, month - 1, date);
            return newDate;
        };

        console.log(body.startDate);
        const newStartDate = convertToDate(body.startDate);
        console.log(newStartDate)
        const newEndDate = convertToDate(body.endDate);
        console.log(newEndDate)

        const CarIdsBookedAtThatTime = await Bookings.find({
            bookingStatus:{$eq:"Cancelled"},
            $or:[{startDate:{$gt:newEndDate}},
            {endDate:{$lt:newStartDate}}],
        }).distinct('carId');

        console.log(CarIdsBookedAtThatTime)

        const filteredCars=await Cars.find({
            _id:{$nin:CarIdsBookedAtThatTime}
        })
        
        console.log(filteredCars);
        res.status(200).json(filteredCars); 
    } catch (err) {
        console.log(err);
        res.status(500).send(err.message); 
    }

    router.delete("car/:id",async(req,res)=>{
        const id=req.params.id
        const carToDelete=Cars.findOne({id:id})
        if(!carToDelete){
            return res.status(404).status("Not found")
        }
        const deletedCar=Cars.findByIdAndDelete(id)

        if(!deletedCar){
            return res.status(404).send("Car not found!")
        }
        res.status(200).send("Car Deleted")
    })




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