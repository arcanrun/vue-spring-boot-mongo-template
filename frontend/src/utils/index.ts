export function formatDate(date: Date) {
  const day = String(date.getDate()).padStart(2, '0')
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const year = date.getFullYear()

  return `${day}-${month}-${year}`
}

export function formatDateFromString(date: string | null | undefined) {
  if (!date) {
    return date
  }

  return formatDate(new Date(date))
}
