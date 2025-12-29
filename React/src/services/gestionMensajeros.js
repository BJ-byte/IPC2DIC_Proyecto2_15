const API_URL = 'http://localhost:8080'

const obtenerMensajeros = async () => {
    try {
        const response = await fetch(`${API_URL}/api/mensajeros`)
        if (!response.ok) {
            throw new Error('Error al obtener los mensajeros');
        }
        return await response.json()
    } catch (error) {
        console.error("Error al obtener los mensajeros", error);
        throw error;
    }
}

const obtenerMensajeroPorId = async (id) => {
    try {
        const response = await fetch(`${API_URL}/api/mensajeros/${id}`)
        if (!response.ok) {
            throw new Error('Error al obtener el mensajero por ID');
        }
        return await response.json()
    } catch (error) {
        console.error("Error al obtener el mensajero por ID", error);
        throw error;
    }
}

const crearMensajero = async (id, nombre, capacidad, centro, estado) => {
    try {
        const payload = { id, nombre, capacidad, centro, estado };
        console.log('Creando mensajero con datos:', payload);
        const response = await fetch(`${API_URL}/api/mensajeros`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload),
        });
        if (!response.ok) {
            const errorText = await response.text();
            console.error('Error del servidor:', response.status, errorText);
            throw new Error(`Error al crear el mensajero: ${response.status} - ${errorText}`);
        }
        return await response.json()
    } catch (error) {
        console.error("Error al crear el mensajero", error);
        throw error;
    }
}

const cambiarEstadoMensajero = async (id, nuevoEstado) => {
    try {
        const response = await fetch(`${API_URL}/api/mensajeros/${id}/estado`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ estado: nuevoEstado }),
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Error al cambiar el estado del mensajero: ${response.status} - ${errorText}`);
        }
        const contentType = response.headers.get('content-type') || ''
        if (contentType.includes('application/json')) {
            return await response.json()
        }
        const text = await response.text()
        try {
            console.log("Id:", id, "Nuevo Estado:", nuevoEstado);
            console.log('Respuesta del servidor al cambiar estado:', text);
            return text ? JSON.parse(text) : { id, estado: nuevoEstado }
        } catch {
            return { message: text || 'Estado actualizado', id, estado: nuevoEstado }
        }
    } catch (error) {
        console.error("Error al cambiar el estado del mensajero", error);
        throw error;
    }
}

const reasignarMensajero = async (id, nuevoCentro) => {
    try {
        const response = await fetch(`${API_URL}/api/mensajeros/${id}/centro`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ centro: nuevoCentro }),
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Error al reasignar el mensajero: ${response.status} - ${errorText}`);
        }
        const contentType = response.headers.get('content-type') || ''
        if (contentType.includes('application/json')) {
            return await response.json()
        }
        const text = await response.text()
        try {
            return text ? JSON.parse(text) : { id, centro: nuevoCentro }
        } catch {
            return { message: text || 'Centro reasignado', id, centro: nuevoCentro }
        }
    } catch (error) {
        console.error("Error al reasignar el mensajero", error);
        throw error;
    }
}

export {
    obtenerMensajeros,
    obtenerMensajeroPorId,
    crearMensajero,
    cambiarEstadoMensajero,
    reasignarMensajero
}