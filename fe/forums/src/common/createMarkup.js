import DOMPurify from "dompurify";

const createMarkup = (richText) => {
  return { __html: DOMPurify.sanitize(richText) };
};
export default createMarkup;
