json.array!(@devices) do |device|
  json.extract! device, :identifier
  json.url device_url(device, format: :json)
end
