json.array!(@magnetic_fields) do |magnetic_field|
  json.extract! magnetic_field, :recording_id, :time, :x, :y, :z
  json.url magnetic_field_url(magnetic_field, format: :json)
end
