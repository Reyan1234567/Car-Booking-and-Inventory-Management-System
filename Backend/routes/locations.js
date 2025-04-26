import { Router } from "express";
import Locations from "../models/locations/js";
const router = Router();

router.get("api/locations", async (req, res) => {
  const { query } = req.query;

  try {
    const results = await Locations.find({
      $or: [
        { name: { $refex: query.search, $options: "i" } },
        { city: { $regex: query.search, $options: "i" } },
        { searchTerms: { $regex: query.search, $options: "i" } },
      ],
    }).limit(10);

    res.status(200).json(results);
  } catch (err) {
    res.status(404).send("Not found");
    console.log(err);
  }
});

export default router;
