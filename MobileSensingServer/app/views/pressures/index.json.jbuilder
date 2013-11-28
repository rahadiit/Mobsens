json.array!(@pressures) do |pressure|
  json.extract! pressure, :recording_id, :time, :pressure
  json.url pressure_url(pressure, format: :json)
end
