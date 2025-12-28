const API_URL = 'http://localhost:8080'

const obtenerRutas = async () => {

    try {
        const response = await fetch(`${API_URL}/api/rutas`)
        if (!response.ok) {
            throw new Error('Error al obtener las rutas');
        }
        const data = await response.json()
        return data;

    } catch (error) {
        console.error("Error al obtener las rutas", error);
        throw error;
    }
}

const obtenerRutaPorId = async (id) => {
    try {
        const response = await fetch(`${API_URL}/api/rutas/${id}`)
        if (!response.ok) {
            throw new Error('Error al obtener la ruta por ID');
        }
        const data = await response.json()
        return data;
    } catch (error) {
        console.error("Error al obtener la ruta por ID", error);
        throw error;
    }
}

const crearRuta = async (ruta) => {
    
}