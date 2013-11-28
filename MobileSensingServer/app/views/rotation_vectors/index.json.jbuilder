json.array!(@rotation_vectors) do |rotation_vector|
  json.extract! rotation_vector, :recording_id, :time, :xSinTheta, :ySinTheta, :zSinTheta
  json.url rotation_vector_url(rotation_vector, format: :json)
end
