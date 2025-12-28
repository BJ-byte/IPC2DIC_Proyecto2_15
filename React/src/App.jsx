import { Routes, Route } from 'react-router-dom'
import './App.css'

// Componentes y vistas (se crean en archivos separados)
import NavBar from './componentes/NavBar.jsx'
import UserServices from './vistas/CargaXml.jsx'
import GestionCentros from './vistas/GestionCentros.jsx'

function App() {
  return (
    <div className="app-container">
      {/* Menú de navegación visible en toda la app */}
      <NavBar />

      <main className="content">
        {/* Definición de rutas: SPA sin recargar la página */}
        <Routes>
          <Route path="/api/importar" element={<UserServices/>} />
          <Route path="/api/centros" element={<GestionCentros />} />
          <Route path="/api/rutas" element={<div>Gestión de rutas</div>} />
          <Route path="/api/mensajeros" element={<div>Gestión de mensajeros</div>} />
          <Route path="/api/paquetes" element={<div>Gestión de paquetes</div>} />
          <Route path="/api/solicitudes" element={<div>Gestión de solicitudes</div>} />
        </Routes>
      </main>
    </div>
  );
}

export default App;