import Home from "@/components/Home";
import LogIn from "@/components/LogIn";
import Registration from "@/components/Registration";
import FishingInstructorProfile from "@/components/FishingInstructor/FishingInstructorProfile";
import AdventureProfile from "@/components/FishingInstructor/AdventureProfile";
import AdventuresOfFishingInstructor from "@/components/FishingInstructor/AdventuresOfFishingInstructor";
import CreateNewAdventure from "@/components/FishingInstructor/CreateNewAdventure";
import HistoryOfReservations from "@/components/FishingInstructor/HistoryOfReservations";

const routes = [
    {path: '/', component: Home},
    {path: '/login', component: LogIn},
    {path: '/registration', component: Registration},
    {path: '/fishingInstructorProfile', component: FishingInstructorProfile},
    {path: '/adventureProfile', component: AdventureProfile},
    {path: '/myAdventures', component: AdventuresOfFishingInstructor},
    {path: '/createAdventure', component: CreateNewAdventure},
    {path: '/reservationHistory', component: HistoryOfReservations}
]

export default routes;