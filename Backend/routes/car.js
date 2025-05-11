import {Router} from "express"
import Cars from "../models/cars.js"
import Bookings from "../models/bookings.js"
import checkAccessToken from "../middleware/checkAccessToken.js";
import carImage from "../models/CarImages.js";
import {upload} from "../middleware/multer.js"
import Car from "../models/cars.js";
const router=Router()


// router.use(checkAccessToken)
//Get All cars
// router.get("/cars",async (req,res)=>{
//     const response=await Cars.find()
//     const carsArray=[]
//     response.forEach((respons)=>{
//         if(respons.image!==null||respons.image!=undefined||respons.image==""){
//             const imageUrl=carImage.findOne(respons.image)
//             if(!imageUrl){
//                 respons.image=""
//             }
//             else{
//                 const carUrl=imageUrl.url
//                 respons.image=carUrl
//             }
//         }
//         carsArray.push(respons)
//     })
//     try{
//         if(!carsArray){
//             return res.status(401).send("No Cars found")
//         }
//         res.status(200).send(carsArray)
//     }
//     catch(err){
//         console.log(err)
//         res.status(401).send(err.message)
//     }
// })

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

router.patch("/cars/:id",async(req,res)=>{
    const id=req.params.id
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
  })
  
    router.delete("/car/:id", async (req, res) => {
      const id = req.params.id;
      try {
          const deletedCar = await Cars.findByIdAndDelete(id);
          if (!deletedCar) {
              return res.status(404).send("Car not found");
          }
          res.status(200).send("Car Deleted");
      } catch (err) {
          console.error(err);
          // Send 500 for server/database errors, 400 for client errors
          const statusCode = err.name === "CastError" ? 400 : 500;
          res.status(statusCode).send(err.message);
      }
  });

router.post("/carImageUpload", upload.single("image"), async (req, res) => {
    console.log("Car image upload initiated");
    try {
      // 1. Check if file exists
      if (!req.file) {
        return res.status(400).json({ error: "No car image uploaded" });
      }
  
      // 2. Create image URL
      const imageUrl = `http://${req.get("host")}/uploads/${req.file.filename}`;
      
      // 3. Save to MongoDB
      const newCarImage = new carImage({
        filename: req.file.filename,
        path: req.file.path,
        size: req.file.size,
        url: imageUrl,  
      });
  
      await newCarImage.save();
  
      // 4. Send success response
      const responseData = {
        url: imageUrl,
        message: "Car image upload successful",
        id: newCarImage._id,
        filename: req.file.filename,
      };
  
      res.status(200).json(responseData);
      console.log("Car image upload success:", responseData);
  
    } catch (err) {
      console.error("Car image upload error:", err);
      res.status(500).json({ 
        error: "Server error during car image upload",
        details: err.message 
      });
    }
  });

  router.post("/cars", async (req, res) => {
    try {
      // Individual field validation
      if (!req.body.name) return res.status(400).json({ error: "Car name is required" });
      if (!req.body.make) return res.status(400).json({ error: "Car make is required" });
      if (!req.body.price) return res.status(400).json({ error: "Price is required" });
      if (isNaN(req.body.price)) return res.status(400).json({ error: "Price must be a number" });
      if (!req.body.model) return res.status(400).json({ error: "Model is required" });
      if (!req.body.year) return res.status(400).json({ error: "Year is required" });
      if (isNaN(req.body.year)) return res.status(400).json({ error: "Year must be a number" });
      if (req.body.year < 1900 || req.body.year > new Date().getFullYear() + 1) {
        return res.status(400).json({ error: "Invalid year" });
      }
      if (!req.body.transmissionType) return res.status(400).json({ error: "Transmission type is required" });
      if (!req.body.fuelType) return res.status(400).json({ error: "Fuel type is required" });
      if (!req.body.passengerCapacity) return res.status(400).json({ error: "Passenger capacity is required" });
      if (isNaN(req.body.passengerCapacity)) return res.status(400).json({ error: "Passenger capacity must be a number" });
      if (!req.body.luggageCapacity) return res.status(400).json({ error: "Luggage capacity is required" });
      if (!req.body.dailyRate) return res.status(400).json({ error: "Daily rate is required" });
      if (isNaN(req.body.dailyRate)) return res.status(400).json({ error: "Daily rate must be a number" });
      if (!req.body.plate) return res.status(400).json({ error: "License plate is required" });
  
      // Check if image reference exists if provided
      if (req.body.imageId) {
        const imageExists = await carImage.findById(req.body.imageId);
        if (!imageExists) {
          return res.status(400).json({ error: "Referenced car image doesn't exist" });
        }
      }
  
      // Create new car
      const newCar = new Cars({
        name: req.body.name,
        make: req.body.make,
        price: req.body.price,
        model: req.body.model,
        year: req.body.year,
        transmissionType: req.body.transmissionType,
        fuelType: req.body.fuelType,
        passengerCapacity: req.body.passengerCapacity,
        luggageCapacity: req.body.luggageCapacity,
        dailyRate: req.body.dailyRate,
        plate: req.body.plate,
        imageUrl: req.body.imageUrl || "",
        image: req.body.imageId || null
      });
  
      await newCar.save();
  
      res.status(201).json({
        message: "Car created successfully",
        car: newCar
      });
  
    } catch (err) {
      // Handle duplicate plate number
      if (err.code === 11000 && err.keyPattern.plate) {
        return res.status(400).json({ error: "This license plate already exists" });
      }
  
      // Handle other errors
      console.error("Car creation error:", err);
      res.status(500).json({ error: "Server error", details: err.message });
    }
  });

router.patch("/car",async(req,res)=>{
    const id=req.params.id
    const body=req.body

    try{
    const carId=Cars.findOne({id:id})
    if(!carId){
        console.log("carId doesn't exist")
        return res.send("Car not found").status(404)
    }
      if (!req.body.name) return res.status(400).json({ error: "Car name is required" });
      if (!req.body.make) return res.status(400).json({ error: "Car make is required" });
      if (!req.body.price) return res.status(400).json({ error: "Price is required" });
      if (isNaN(req.body.price)) return res.status(400).json({ error: "Price must be a number" });
      if (!req.body.model) return res.status(400).json({ error: "Model is required" });
      if (!req.body.year) return res.status(400).json({ error: "Year is required" });
      if (isNaN(req.body.year)) return res.status(400).json({ error: "Year must be a number" });
      if (req.body.year < 1900 || req.body.year > new Date().getFullYear() + 1) {
        return res.status(400).json({ error: "Invalid year" });
      }
      if (!req.body.transmissionType) return res.status(400).json({ error: "Transmission type is required" });
      if (!req.body.fuelType) return res.status(400).json({ error: "Fuel type is required" });
      if (!req.body.passengerCapacity) return res.status(400).json({ error: "Passenger capacity is required" });
      if (isNaN(req.body.passengerCapacity)) return res.status(400).json({ error: "Passenger capacity must be a number" });
      if (!req.body.luggageCapacity) return res.status(400).json({ error: "Luggage capacity is required" });
      if (!req.body.dailyRate) return res.status(400).json({ error: "Daily rate is required" });
      if (isNaN(req.body.dailyRate)) return res.status(400).json({ error: "Daily rate must be a number" });
      if (!req.body.plate) return res.status(400).json({ error: "License plate is required" });
    if(!body.carImage){
        delete body.carImage
    }
    const updates=Cars.findByIdAndUpdate(id, body,{
        new:true,
        runValidators:true
    })
    if(!updates){
        return res.status(404).send("Car not found")
    }
    res.status(200).send(updates)
    }
    catch(err){
        res.status(400).send(err.message)
        console.log(err)
    }


})

router.get("/cars",async(req,res)=>{
  try {
    const final=await Car.aggregate([
      {$lookup:{
        from:"carimages",
        localField:"image",
        foreignField:"_id",
        as:"CI"
      }},{$unwind:{path:"$CI", preserveNullAndEmptyArrays:true}}
    ])
    if(!final){
      console.log("no car image or cars ig")
      return res.status(404).send("Not found")
    }
    res.status(200).send(final)
    console.log("cars outputted successfully ", final)
  } 
  catch (e) {
    res.status(400).send(e)
    console.log(e)
  }
})







export default router;












