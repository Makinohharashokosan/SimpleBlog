/**
 * Estimates reading time for a given text.
 * @param {string} text The text content.
 * @param {number} wordsPerMinute The average reading speed.
 * @returns {number} Estimated reading time in minutes.
 */
export function calculateReadingTime(text = '', wordsPerMinute = 200) {
  if (!text) return 0;

  // A simple regex to count words, handles CJK characters as single words
  const words = text.match(/[\w\u4e00-\u9fa5]+/g);
  const wordCount = words ? words.length : 0;

  return Math.ceil(wordCount / wordsPerMinute);
}