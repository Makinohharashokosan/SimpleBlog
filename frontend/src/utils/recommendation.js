/**
 * Calculate related articles based on a scoring system.
 * Priority:
 * 1. Matching Tags (High weight)
 * 2. Featured Status (Medium weight)
 * 3. View Count (Low weight, tie-breaker)
 * 
 * @param {Object} currentArticle - The article currently being viewed.
 * @param {Array} candidates - List of potential related articles.
 * @param {Number} limit - Max number of articles to return.
 * @returns {Array} Sorted list of related articles.
 */
export const getRelatedArticles = (currentArticle, candidates, limit = 5) => {
    if (!currentArticle || !candidates || !Array.isArray(candidates)) {
        return [];
    }

    // Filter out the current article from candidates
    const filteredCandidates = candidates.filter(item => item.id !== currentArticle.id);

    const scoredArticles = filteredCandidates.map(article => {
        let score = 0;

        // 1. Tag Similarity (10 points per matching tag)
        if (currentArticle.tags && article.tags) {
            const currentTags = new Set(currentArticle.tags.map(t => t.name));
            const matchingTags = article.tags.filter(t => currentTags.has(t.name));
            score += matchingTags.length * 10;
        }

        // 2. Featured Status (5 points)
        if (article.featured) {
            score += 5;
        }

        // 3. View Count (0.001 points per view - mainly as tie breaker)
        if (article.viewCount) {
            score += article.viewCount * 0.001;
        }

        // 4. Recency (Optional tie breaker: 1 point if from same month? - skipping for now to keep simple)

        return { ...article, _relevanceScore: score };
    });

    // Sort by score descending
    scoredArticles.sort((a, b) => b._relevanceScore - a._relevanceScore);

    // Return top N, removing the score property if needed (optional, keeping it clean)
    return scoredArticles.slice(0, limit);
};
