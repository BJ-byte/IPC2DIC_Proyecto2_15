import React, { useState } from 'react'
import { obtenerCentros, obtenerCentroPorId, obtenerPaquetesDelCentro, obtenerMensajerosDelCentro } from '../services/gestionCentros'

const GestionCentros = () => {

    const [estadoError, setEstadoError] = useState(null)
    const [estadoSuccess, setEstadoSuccess] = useState(null)

    const [centros, setCentros] = useState([])
    const [centro, setCentro] = useState(null)

    // Datos anidados por centro
    const [paquetesPorCentro, setPaquetesPorCentro] = useState({}) // { [id]: array }
    const [mensajerosPorCentro, setMensajerosPorCentro] = useState({}) // { [id]: array }
    const [loadingPaquetes, setLoadingPaquetes] = useState({}) // { [id]: boolean }
    const [loadingMensajeros, setLoadingMensajeros] = useState({}) // { [id]: boolean }
    const [erroresPaquetes, setErroresPaquetes] = useState({}) // { [id]: string|null }
    const [erroresMensajeros, setErroresMensajeros] = useState({}) // { [id]: string|null }

    const [centroIdInput, setCentroIdInput] = useState('')

    // CARGAR TODOS LOS CENTROS
    const mostrarCentros = async () => {
        try {
            limpiarEstados()
            const data = await obtenerCentros()
            console.log('Centros recibidos:', data)
            setCentros(data)
            setCentro(null)
            // limpiar detalles anidados
            setPaquetesPorCentro({})
            setMensajerosPorCentro({})
            setLoadingPaquetes({})
            setLoadingMensajeros({})
            setErroresPaquetes({})
            setErroresMensajeros({})
            setEstadoSuccess('Centros cargados correctamente')
        } catch {
            setEstadoError('Error al cargar los centros')
        }
    }

    // CARGAR CENTRO POR ID
    const mostrarCentroPorId = async (id) => {
        try {
            limpiarEstados()
            const data = await obtenerCentroPorId(id)
            setCentro(data)
            setCentros([])
            // limpiar detalles anidados
            setPaquetesPorCentro({})
            setMensajerosPorCentro({})
            setLoadingPaquetes({})
            setLoadingMensajeros({})
            setErroresPaquetes({})
            setErroresMensajeros({})
            setEstadoSuccess('Centro cargado correctamente')
        } catch {
            setEstadoError('Error al cargar el centro')
        }
    }

    // LISTAR PAQUETES
    const listarPaquetesDelCentro = async (id) => {
        try {
            // estado local por centro
            setLoadingPaquetes(prev => ({ ...prev, [id]: true }))
            setErroresPaquetes(prev => ({ ...prev, [id]: null }))
            const data = await obtenerPaquetesDelCentro(id)
            setPaquetesPorCentro(prev => ({ ...prev, [id]: data }))
            setEstadoSuccess(null)
        } catch {
            setErroresPaquetes(prev => ({ ...prev, [id]: 'Error al cargar los paquetes' }))
        } finally {
            setLoadingPaquetes(prev => ({ ...prev, [id]: false }))
        }
    }

    // LISTAR MENSAJEROS
    const listarMensajerosDelCentro = async (id) => {
        try {
            setLoadingMensajeros(prev => ({ ...prev, [id]: true }))
            setErroresMensajeros(prev => ({ ...prev, [id]: null }))
            const data = await obtenerMensajerosDelCentro(id)
            setMensajerosPorCentro(prev => ({ ...prev, [id]: data }))
            setEstadoSuccess(null)
        } catch {
            setErroresMensajeros(prev => ({ ...prev, [id]: 'Error al cargar los mensajeros' }))
        } finally {
            setLoadingMensajeros(prev => ({ ...prev, [id]: false }))
        }
    }

    const limpiarEstados = () => {
        setEstadoError(null)
        setEstadoSuccess(null)
        // no limpiar los anidados aquí para no borrar al alternar
    }

    // RENDER
    return (
        <section>
            <h1>Gestión de Centros</h1>

            <button onClick={mostrarCentros}>Cargar Centros</button>

            <h3>Buscar centro por ID</h3>
            <input
                placeholder="ID del centro"
                value={centroIdInput}
                onChange={(e) => setCentroIdInput(e.target.value)}
            />
            <button onClick={() =>
                mostrarCentroPorId(centroIdInput)
            }>
                Buscar Centro
            </button>

            {/* MENSAJES */}
            {estadoError && <p style={{ color: 'red' }}>{estadoError}</p>}
            {estadoSuccess && <p style={{ color: 'green' }}>{estadoSuccess}</p>}

            {/* LISTA DE CENTROS */}
            {centros.length > 0 && (
                <ul>
                    {centros.map(c => (
                        <div key={c.id} className="card">
                            <h3>{c.nombre}</h3>
                            <p>ID: {c.id}</p>
                            <p>Capacidad máxima: {c.capacidad}</p>
                            <p>Capacidad actual: {Number(c.capacidad) - Number(c.cargaActual)}</p>
                            <p>% Uso: {c.porcentajeUso}%</p>
                            <button onClick={() => listarPaquetesDelCentro(c.id)}>Paquetes</button>
                            <button onClick={() => listarMensajerosDelCentro(c.id)}>Mensajeros</button>

                            {/* Paquetes del centro */}
                            {loadingPaquetes[c.id] && <p>Cargando paquetes...</p>}
                            {erroresPaquetes[c.id] && <p style={{ color: 'red' }}>{erroresPaquetes[c.id]}</p>}
                            {Array.isArray(paquetesPorCentro[c.id]) && paquetesPorCentro[c.id].length > 0 && (
                                <ul>
                                    {paquetesPorCentro[c.id].map(p => (
                                        <li key={p.id}>Paquete #{p.id}</li>
                                    ))}
                                </ul>
                            )}

                            {/* Mensajeros del centro */}
                            {loadingMensajeros[c.id] && <p>Cargando mensajeros...</p>}
                            {erroresMensajeros[c.id] && <p style={{ color: 'red' }}>{erroresMensajeros[c.id]}</p>}
                            {Array.isArray(mensajerosPorCentro[c.id]) && mensajerosPorCentro[c.id].length > 0 && (
                                <ul>
                                    {mensajerosPorCentro[c.id].map(m => (
                                        <li key={m.id}>Mensajero #{m.id}</li>
                                    ))}
                                </ul>
                            )}
                        </div>
                    ))}
                </ul>
            )}

            {/* CENTRO INDIVIDUAL */}
            {centro && (
                <div className="card">
                    <h3>{centro.nombre}</h3>
                    <p>ID: {centro.id}</p>
                    <p>Capacidad máxima: {centro.capacidad}</p>
                    <p>Capacidad actual: {Number(centro.capacidad) - Number(centro.cargaActual)}</p>
                    <p>% Uso: {centro.porcentajeUso}%</p>
                    <button onClick={() => listarPaquetesDelCentro(centro.id)}>Paquetes</button>
                    <button onClick={() => listarMensajerosDelCentro(centro.id)}>Mensajeros</button>

                    {/* Paquetes del centro seleccionado */}
                    {loadingPaquetes[centro.id] && <p>Cargando paquetes...</p>}
                    {erroresPaquetes[centro.id] && <p style={{ color: 'red' }}>{erroresPaquetes[centro.id]}</p>}
                    {Array.isArray(paquetesPorCentro[centro.id]) && paquetesPorCentro[centro.id].length > 0 && (
                        <ul>
                            {paquetesPorCentro[centro.id].map(p => (
                                <li key={p.id}>Paquete #{p.id}</li>
                            ))}
                        </ul>
                    )}
    
                    {/* Mensajeros del centro seleccionado */}
                    {loadingMensajeros[centro.id] && <p>Cargando mensajeros...</p>}
                    {erroresMensajeros[centro.id] && <p style={{ color: 'red' }}>{erroresMensajeros[centro.id]}</p>}
                    {Array.isArray(mensajerosPorCentro[centro.id]) && mensajerosPorCentro[centro.id].length > 0 && (
                        <ul>
                            {mensajerosPorCentro[centro.id].map(m => (
                                <li key={m.id}>Mensajero #{m.id}</li>
                            ))}
                        </ul>
                    )}
                </div>
            )}
        </section>
    )
}

export default GestionCentros
