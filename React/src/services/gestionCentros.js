const API_URL = 'http://localhost:8080'

export const obtenerCentros = async () => {
    try {
        const response = await fetch(`${API_URL}/api/centros`)
        if (!response.ok) {
            throw new Error(`Error al obtener centros: ${response.status}`)
        }
        return await response.json()
    } catch (error) {
        console.error('Error al obtener centros', error)
        throw error
    }
}

export const obtenerCentroPorId = async (id) => {
    try {
        const response = await fetch(`${API_URL}/api/centros/${encodeURIComponent(id)}`)
        if (!response.ok) {
            throw new Error(`Error al obtener el centro por ID: ${response.status}`)
        }
        return await response.json()
    } catch (error) {
        console.error('Error al obtener el centro por ID', error)
        throw error
    }
}

export const obtenerPaquetesDelCentro = async (id) => {
    try {
        const response = await fetch(`${API_URL}/api/centros/${encodeURIComponent(id)}/paquetes`)
        if (!response.ok) {
            throw new Error(`Error al obtener los paquetes del centro: ${response.status}`)
        }
        return await response.json()
    } catch (error) {
        console.error('Error al obtener los paquetes del centro', error)
        throw error
    }
}

export const obtenerMensajerosDelCentro = async (id) => {
    try {
        const response = await fetch(`${API_URL}/api/centros/${encodeURIComponent(id)}/mensajeros`)
        if (!response.ok) {
            throw new Error(`Error al obtener los mensajeros del centro: ${response.status}`)
        }
        return await response.json()
    } catch (error) {
        console.error('Error al obtener los mensajeros del centro', error)
        throw error
    }
}