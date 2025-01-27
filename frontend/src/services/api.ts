import axios from "axios";
import { API_BASE_URL } from "../utils";

export const api = axios.create({
    baseURL: API_BASE_URL
});
