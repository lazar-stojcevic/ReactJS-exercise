import Home from "@/components/Home";
import LogIn from "@/components/LogIn";
import Registration from "@/components/Registration";
import FishingInstructorProfile from "@/components/FishingInstructor/FishingInstructorProfile";
import AdventureProfile from "@/components/FishingInstructor/AdventureProfile";
import AdventuresOfFishingInstructor from "@/components/FishingInstructor/AdventuresOfFishingInstructor";
import CreateNewAdventure from "@/components/FishingInstructor/CreateNewAdventure";

const routes = [
    {path: '/', component: Home},
    {path: '/login', component: LogIn},
    {path: '/registration', component: Registration},
    {path: '/fishingInstructorProfile', component: FishingInstructorProfile},
    {path: '/adventureProfile', component: AdventureProfile},
    {path: '/myAdventures', component: AdventuresOfFishingInstructor},
    {path: '/createAdventure', component: CreateNewAdventure}
]

export default routes;