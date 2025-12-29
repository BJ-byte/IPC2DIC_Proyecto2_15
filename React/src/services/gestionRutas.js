const API_URL = 'http://localhost:8080'

const obtenerRutas = async () => {

    try {
        const response = await fetch(`${API_URL}/api/rutas`)
        if (!response.ok) {
            throw new Error('Error al obtener las rutas');
        }
        const contentType = response.headers.get('content-type') || ''
        if (contentType.includes('application/json')) {
            return await response.json()
        }
        const text = await response.text()
        try {
            return text ? JSON.parse(text) : []
        } catch {
            throw new Error(`Respuesta no válida del servidor (esperado JSON): ${text}`)
        }

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
        const contentType = response.headers.get('content-type') || ''
        if (contentType.includes('application/json')) {
            return await response.json()
        }
        const text = await response.text()
        try {
            return text ? JSON.parse(text) : null
        } catch {
            throw new Error(`Respuesta no válida del servidor (esperado JSON): ${text}`)
        }
    } catch (error) {
        console.error("Error al obtener la ruta por ID", error);
        throw error;
    }
}

const crearRuta = async (id, origen, destino, distancia) => {
    try {
        const response = await fetch(`${API_URL}/api/rutas`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ id, origen, destino, distancia }),
        });
        if (!response.ok) {
            throw new Error('Error al crear la ruta');
        }
        const contentType = response.headers.get('content-type') || ''
        if (contentType.includes('application/json')) {
            return await response.json()
        }
        const text = await response.text()
        try {
            return text ? JSON.parse(text) : { id, origen, destino, distancia }
        } catch {
            return { message: text || 'Ruta creada', id, origen, destino, distancia }
        }

    } catch (error) {
        console.error("Error al crear la ruta", error);
        throw error;
    }
}

const actualizarRuta = async (id, origen, destino, distancia) => {
    try {
        const response = await fetch(`${API_URL}/api/rutas/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ origen, destino, distancia }),
        });
        if (!response.ok) {
            throw new Error('Error al actualizar la ruta');
        }
        const contentType = response.headers.get('content-type') || ''
        if (contentType.includes('application/json')) {
            return await response.json()
        }
        const text = await response.text()
        try {
            return text ? JSON.parse(text) : { id, origen, destino, distancia }
        } catch {
            return { message: text || 'Ruta actualizada', id, origen, destino, distancia }
        }
    } catch (error) {
        console.error("Error al actualizar la ruta", error);
        throw error;
    }
}

const eliminarRuta = async (id) => {
    try {
        const response = await fetch(`${API_URL}/api/rutas/${id}`, {
            method: 'DELETE',
        });
        if (!response.ok) {
            throw new Error('Error al eliminar la ruta');
        }
        return;
    } catch (error) {
        console.error("Error al eliminar la ruta", error);
        throw error;
    }
}

export {
    obtenerRutas,
    obtenerRutaPorId,
    crearRuta,
    actualizarRuta,
    eliminarRuta
}