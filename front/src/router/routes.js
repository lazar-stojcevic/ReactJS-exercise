import Home from "@/components/Home";
import LogIn from "@/components/LogIn";
import Registration from "@/components/Registration";

const routes = [
    {path: '/', component: Home},
    {path: '/login', component: LogIn},
    {path: '/registration', component: Registration}
]

export default routes;