const API_URL = 'http://localhost:8080'

const obtenerPaquetes = async () => {
    try {
        const response = await fetch(`${API_URL}/api/paquetes`)
        if (!response.ok) {
            throw new Error('Error al obtener los paquetes');
        }
        return await response.json()
    } catch (error) {
        console.error("Error al obtener los paquetes", error);
        throw error;
    }
}

const obtenerPaquetePorId = async (id) => {
    try {
        const response = await fetch(`${API_URL}/api/paquetes/${id}`)
        if (!response.ok) {
            throw new Error('Error al obtener el paquete por ID');
        }
        return await response.json()
    } catch (error) {
        console.error("Error al obtener el paquete por ID", error);
        throw error;
    }
}

const crearPaquete = async (id, cliente, peso, destino, estado, centro) => {
    try {
        const payload = { id, cliente, peso, destino, estado, centroActual: centro };
        console.log('Creando paquete con datos:', payload);
        const response = await fetch(`${API_URL}/api/paquetes`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload),
        });
        if (!response.ok) {
            const errorText = await response.text();
            console.error('Error del servidor:', response.status, errorText);
            throw new Error(`Error al crear el paquete: ${response.status} - ${errorText}`);
        }
        const contentType = response.headers.get('content-type') || ''
        if (contentType.includes('application/json')) {
            return await response.json()
        }
        const text = await response.text()
        try {
            return text ? JSON.parse(text) : { message: 'Paquete creado', id, cliente, peso, destino, estado, centroActual: centro }
        } catch {
            return { message: text || 'Paquete creado', id, cliente, peso, destino, estado, centro }
        }
    } catch (error) {
        console.error("Error al crear el paquete", error);
        throw error;
    }
}

const actualizarPaquete = async (id, cliente, peso, destino, estado, centro) => {
    try {
        const response = await fetch(`${API_URL}/api/paquetes/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ cliente, peso, destino, estado, centroActual: centro }),
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Error al actualizar el paquete: ${response.status} - ${errorText}`);
        }
        const contentType = response.headers.get('content-type') || ''
        if (contentType.includes('application/json')) {
            return await response.json()
        }
        const text = await response.text()
        try {
            return text ? JSON.parse(text) : { message: 'Paquete actualizado', id, cliente, peso, destino, estado, centroActual: centro }
        } catch {
            return { message: text || 'Paquete actualizado', id, cliente, peso, destino, estado, centro }
        }
    } catch (error) {
        console.error("Error al actualizar el paquete", error);
        throw error;
    }
}

const eliminarPaquete = async (id) => {
    try {
        const response = await fetch(`${API_URL}/api/paquetes/${id}`, {
            method: 'DELETE',
        });
        if (!response.ok) {
            throw new Error('Error al eliminar el paquete');
        }
        return;
    } catch (error) {
        console.error("Error al eliminar el paquete", error);
        throw error;
    }
}

export {
    obtenerPaquetes,
    obtenerPaquetePorId,
    crearPaquete,
    actualizarPaquete,
    eliminarPaquete
}