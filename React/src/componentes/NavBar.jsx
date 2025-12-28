import { NavLink } from 'react-router-dom'

// Menú de navegación principal.
// Usa NavLink para marcar la ruta activa sin recargar la página.
export default function NavBar() {
  return (
    <header className="navbar">
      <nav className="navbar-inner">
        <span className="brand">Menu</span>
        <ul className="nav-links">
          <li>
            <NavLink
              to="/api/importar"
              className={({ isActive }) => (isActive ? 'active' : undefined)}
              end
            >
              Importar XML
            </NavLink>
          </li>
        </ul>
      </nav>
    </header>
  )
}
