import { Router } from "express";
import Locations from "../models/locations.js";
import checkAccessToken from "../middleware/checkAccessToken.js";

const router = Router();

router.use(checkAccessToken)

router.get("/api/locations", async (req, res) => {
  const { search } = req.query;

  try {
    console.log("THIS IS THE QUERY:"+ search)
    const results = await Locations.find({
      $or: [
        { name: { $regex: search, $options: "i" } },
        { city: { $regex: search, $options: "i" } },
        { searchTerms: { $regex: search, $options: "i" } },
      ],
    }).limit(10);

    res.status(200).json(results);
  } catch (err) {
    res.status(404).send("Not found");
    console.log(err);
  }
});

export default router;
