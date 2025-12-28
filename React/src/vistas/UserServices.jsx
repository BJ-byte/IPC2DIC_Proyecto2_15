import { useState } from 'react'
import { cargarXml } from '../services/services.js'

export default function Users() {

    const [xmlFile, setXmlFile] = useState(null)
    const [xmlError, setXmlError] = useState('')
    const [xmlSuccess, setXmlSuccess] = useState('')

    const handleXmlSubmit = async (e) => {
        e.preventDefault()
        if (!xmlFile) {
            setXmlError('Selecciona un archivo XML.')
            return
        }
        try {
            const data = await cargarXml(xmlFile)
            setXmlSuccess('XML cargado correctamente.')
        } catch (err) {
            setXmlError('Catched. Error al cargar el XML. Detalles: ' + err.message)
        }
    }

    return (
        <div>
            <form onSubmit={handleXmlSubmit} style={{ marginBottom: '1.5rem', textAlign: 'center' }}>
                <input
                    type="file"
                    accept=".xml"
                    onChange={e => setXmlFile(e.target.files[0])}
                    style={{ marginRight: '1rem' }}
                />
                <button type="submit" className="btn primary" style={{ width: 'auto' }}>
                    Cargar XML
                </button>
            </form>
            {xmlError && <p className="error-message">{xmlError}</p>}
            {xmlSuccess && <p className="success-message">{xmlSuccess}</p>}
        </div>
    )
}