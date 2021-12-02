const ValidationMessage = ({ errorResponse, field }) => {
  return (
    <>
      {!!errorResponse[field] ? (
        <p className="validation-message">{errorResponse[field]}</p>
      ) : (
        ""
      )}
    </>
  );
};
export default ValidationMessage;
