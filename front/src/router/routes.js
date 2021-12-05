import Home from "@/components/Home";
import LogIn from "@/components/LogIn";
import Registration from "@/components/Registration";
import FishingInstructorProfile from "@/components/FishingInstructor/FishingInstructorProfile";
import AdventureProfile from "@/components/FishingInstructor/AdventureProfile";
import CustomerProfile from "@/components/Customer/CustomerProfile";
import AllCottages from "@/components/Customer/AllCottages";
import AllBoats from "@/components/Customer/AllBoats";
import AllInstructors from "@/components/Customer/AllInstructors";

const routes = [
    {path: '/', component: Home},
    {path: '/login', component: LogIn},
    {path: '/registration', component: Registration},
    {path: '/fishingInstructorProfile', component: FishingInstructorProfile},
    {path: '/adventureProfile', component: AdventureProfile},
    {path: '/customerProfile', component: CustomerProfile},
    {path: '/allcottages', component: AllCottages},
    {path: '/allboats', component: AllBoats},
    {path: '/allinstructors', component: AllInstructors},
]

export default routes;
