import { Routes, Route } from 'react-router-dom'
import './App.css'

// Componentes y vistas (se crean en archivos separados)
import NavBar from './componentes/NavBar.jsx'
import UserServices from './vistas/UserServices.jsx'

function App() {
  return (
    <div className="app-container">
      {/* Menú de navegación visible en toda la app */}
      <NavBar />

      <main className="content">
        {/* Definición de rutas: SPA sin recargar la página */}
        <Routes>
          <Route path="/api/importar" element={<UserServices/>} />
        </Routes>
      </main>
    </div>
  );
}

export default App;