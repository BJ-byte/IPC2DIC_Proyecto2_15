import { NavLink } from 'react-router-dom'

// Menú de navegación principal.
// Usa NavLink para marcar la ruta activa sin recargar la página.
export default function NavBar() {
  return (
    <header>
      <nav>
        <span>Menu</span>
        <ul>
          <li>
            <NavLink
              to="/api/importar"
              end
            >
              Importar XML
            </NavLink>
          </li>
        </ul>
        <ul>
          <li>
            <NavLink
              to="/api/centros"
              end
            >
              Gestion de centros
            </NavLink>
          </li>
        </ul>
        <ul>
          <li>
            <NavLink
              to="/api/rutas"
              end
            >
              Gestion de rutas
            </NavLink>
          </li>
        </ul>
        <ul>
          <li>
            <NavLink
              to="/api/mensajeros"
              end
            >
              Gestion de mensajeros
            </NavLink>
          </li>
        </ul>
        <ul>
          <li>
            <NavLink
              to="/api/paquetes"
              end
            >
              Gestion de paquetes
            </NavLink>
          </li>
        </ul>
        <ul>
          <li>
            <NavLink
              to="/api/solicitudes"
              end
            >
              Gestion de solicitudes
            </NavLink>
          </li>
        </ul>
      </nav>
    </header>
  )
}
