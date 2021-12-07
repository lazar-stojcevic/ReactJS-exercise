import Home from "@/components/Home";
import LogIn from "@/components/LogIn";
import Registration from "@/components/Registration";
import FishingInstructorProfile from "@/components/FishingInstructor/FishingInstructorProfile";
import AdventureProfile from "@/components/FishingInstructor/AdventureProfile";
import CustomerProfile from "@/components/Customer/CustomerProfile";
import AllCottages from "@/components/Customer/AllCottages";
import AllBoats from "@/components/Customer/AllBoats";
import AllInstructors from "@/components/Customer/AllInstructors";
import CottageReservations from "@/components/Customer/CottageReservations";
import BoatReservations from "@/components/Customer/BoatReservations";
import AdventureReservations from "@/components/Customer/AdventureReservations";
import BookedReservations from "@/components/Customer/BookedReservations";
import PenaltyPoints from "@/components/Customer/PenaltyPoints";
import Subscriptions from "@/components/Customer/Subscriptions";
import Complaint from "@/components/Customer/Complaint";
import AdventuresOfFishingInstructor from "@/components/FishingInstructor/AdventuresOfFishingInstructor";
import CreateNewAdventure from "@/components/FishingInstructor/CreateNewAdventure";
import HistoryOfReservations from "@/components/FishingInstructor/HistoryOfReservations";

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
    {path: '/cottageReservations', component: CottageReservations},
    {path: '/boatReservations', component: BoatReservations},
    {path: '/adventureReservations', component: AdventureReservations},
    {path: '/bookedReservations', component: BookedReservations},
    {path: '/penaltyPoints', component: PenaltyPoints},
    {path: '/subscriptions', component: Subscriptions},
    {path: '/complaint', component: Complaint},
    {path: '/myAdventures', component: AdventuresOfFishingInstructor},
    {path: '/createAdventure', component: CreateNewAdventure},
    {path: '/reservationHistory', component: HistoryOfReservations}
]

export default routes;
