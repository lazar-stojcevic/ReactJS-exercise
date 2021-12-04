import Home from "@/components/Home";
import LogIn from "@/components/LogIn";
import Registration from "@/components/Registration";
import FishingInstructorProfile from "@/components/FishingInstructor/FishingInstructorProfile";
import AdventureProfile from "@/components/FishingInstructor/AdventureProfile";
import CustomerProfile from "@/components/Customer/CustomerProfile";

const routes = [
    {path: '/', component: Home},
    {path: '/login', component: LogIn},
    {path: '/registration', component: Registration},
    {path: '/fishingInstructorProfile', component: FishingInstructorProfile},
    {path: '/adventureProfile', component: AdventureProfile},
    {path: '/customerProfile', component: CustomerProfile},
]

export default routes;
