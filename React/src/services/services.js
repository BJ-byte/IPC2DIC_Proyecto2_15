const API_URL = 'http://localhost:8080'

export const cargarXml = async (file) => {
    const formData = new FormData();
    formData.append('file', file);

    try{
        const response = await fetch(`${API_URL}/api/importar`,{
            method: 'POST',
            body: formData
    });

    if (!response.ok){
        throw new Error('Error al cargar archivo desde el xml');

    }

    const data = await response.json()
    return data;
    
    }catch(error){
        console.error("Error al cargar usuarios desde el xml", error);
        throw error;
    }
}