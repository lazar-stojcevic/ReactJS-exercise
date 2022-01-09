import Home from "@/components/Home";
import LogIn from "@/components/LogIn";
import Registration from "@/components/Registration";
import FishingInstructorProfile from "@/components/FishingInstructor/FishingInstructorProfile";
import AdventureProfile from "@/components/FishingInstructor/AdventureProfile";
import CustomerProfile from "@/components/Customer/CustomerProfile";
import AllCottages from "@/components/Customer/ShowAll/AllCottages";
import AllBoats from "@/components/Customer/ShowAll/AllBoats";
import AllFishingInstructors from "@/components/Customer/ShowAll/AllFishingInstructors";
import CottageReservations from "@/components/Customer/PastReservations/CottageReservations";
import BoatReservations from "@/components/Customer/PastReservations/BoatReservations";
import AdventureReservations from "@/components/Customer/PastReservations/AdventureReservations";
import BookedReservations from "@/components/Customer/BookedReservations";
import PenaltyPoints from "@/components/Customer/PenaltyPoints";
import Subscriptions from "@/components/Customer/Subscriptions";
import Complaint from "@/components/Customer/Complaint";
import AdventuresOfFishingInstructor from "@/components/FishingInstructor/AdventuresOfFishingInstructor";
import CreateNewAdventure from "@/components/FishingInstructor/CreateNewAdventure";
import HistoryOfReservations from "@/components/FishingInstructor/HistoryOfReservations";
import OneBoat from "@/components/Customer/ShowOne/OneBoat";
import OneInstructor from "@/components/Customer/ShowOne/OneInstructor";
import OneCottage from "@/components/Customer/ShowOne/OneCottage";
import ReserveAdventure from "@/components/Customer/Reserve/ReserveAvanture";
import NewAdventureReservation from "@/components/Customer/Reserve/NewAdventureReservation";
import InstructorActions from "@/components/Customer/Actions/InstructorActions";
import ReserveCottage from "@/components/Customer/Reserve/ReserveCottage";
import CottageActions from "@/components/Customer/Actions/CottageActions";
import ReserveBoat from "@/components/Customer/Reserve/ReserveBoat";
import BoatActions from "@/components/Customer/Actions/BoatActions";


import AdminProfile from "@/components/Admin/AdminProfile";
import ProfileRequests from "@/components/Admin/Requests";
import CreateNewAdmin from "@/components/Admin/CreateNewAdmin";
import CottageOwnerProfile from "../components/CottageOwner/CottageOwnerProfile";
import CreateCottage from "../components/CottageOwner/CreateCottage";
import UsersCottage from "../components/CottageOwner/UsersCottage";
import CottageProfile from "../components/CottageOwner/CottageProfile";
import MyCottageReservation from "../components/CottageOwner/MyCottageReservation";
import Availability from "../components/CottageOwner/Availability";
import UserReservationProfile from "../components/CottageOwner/UserReservationProfile";
import AvailablePeriodsWithBenefits from "../components/CottageOwner/AvailablePeriodsWithBenefits";
import ReservationReport from "../components/CottageOwner/ReservationReport";
import CreateReservation from "../components/CottageOwner/CreateReservation";
import BoatOwnerProfile from "../components/BoatOwner/BoatOwnerProfile";
import UsersBoats from "../components/BoatOwner/UsersBoats";
import CreateBoat from "../components/BoatOwner/CreateBoat";

const routes = [
    {path: '/', component: Home},
    {path: '/login', component: LogIn},
    {path: '/registration', component: Registration},
    {path: '/fishingInstructorProfile', component: FishingInstructorProfile},
    {path: '/adventureProfile', component: AdventureProfile},
    {path: '/customerProfile', component: CustomerProfile},
    {path: '/allcottages', component: AllCottages},
    {path: '/allboats', component: AllBoats},
    {path: '/allinstructors', component: AllFishingInstructors},
    {path: '/cottageReservations', component: CottageReservations},
    {path: '/boatReservations', component: BoatReservations},
    {path: '/adventureReservations', component: AdventureReservations},
    {path: '/bookedReservations', component: BookedReservations},
    {path: '/penaltyPoints', component: PenaltyPoints},
    {path: '/subscriptions', component: Subscriptions},
    {path: '/complaint', component: Complaint},
    {path: '/myAdventures', component: AdventuresOfFishingInstructor},
    {path: '/createAdventure', component: CreateNewAdventure},
    {path: '/reservationHistory', component: HistoryOfReservations},
    {path: '/cottage/:id', component: OneCottage},
    {path: '/boat/:id', component: OneBoat},
    {path: '/instructor/:id', component: OneInstructor},
    {path: '/cottage/:id', component: OneCottage},
    {path: '/adminProfile', component: AdminProfile},
    {path: '/profileRequests', component: ProfileRequests},
    {path: '/createNewAdmin', component: CreateNewAdmin},
    {path: '/createCottage', component: CreateCottage},
    {path: '/usersCottage', component: UsersCottage},
    {path: '/cottageProfile/:id', component: CottageProfile},
    {path: '/myCottageReservation', component: MyCottageReservation},
    {path: '/availability', component: Availability},
    {path: '/cottageOwnerProfile', component: CottageOwnerProfile},
    {path: '/boatOwnerProfile', component: BoatOwnerProfile},
    {path: '/userReservationProfile', component: UserReservationProfile},
    {path: '/availablePeriodsWithBenefits', component: AvailablePeriodsWithBenefits},
    {path: '/reservationReport', component: ReservationReport},
    {path: '/createReservationForCustomer', component: CreateReservation},
    {path: '/reserveAdventure', component: ReserveAdventure},
    {path: '/newAdventureReservation/:id', component: NewAdventureReservation},
    {path: '/instructorActions/:id', component: InstructorActions},
    {path: '/reserveCottage', component: ReserveCottage},
    {path: '/usersBoats', component: UsersBoats},
    {path: '/createBoats', component: CreateBoat},
    {path: '/cottageActions/:id', component: CottageActions},
    {path: '/boatActions/:id', component: BoatActions},
    {path: '/reserveBoat', component: ReserveBoat},
]

export default routes;
