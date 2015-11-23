/**
 * This class is generated by jOOQ
 */
package com.selfach.dao.jooq.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.5" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CameraRecord extends org.jooq.impl.UpdatableRecordImpl<com.selfach.dao.jooq.tables.records.CameraRecord> implements org.jooq.Record9<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.Integer> {

	private static final long serialVersionUID = 1595854358;

	/**
	 * Setter for <code>selfach.Camera.id</code>.
	 */
	public void setId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>selfach.Camera.id</code>.
	 */
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>selfach.Camera.name</code>.
	 */
	public void setName(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>selfach.Camera.name</code>.
	 */
	public java.lang.String getName() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>selfach.Camera.url</code>.
	 */
	public void setUrl(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>selfach.Camera.url</code>.
	 */
	public java.lang.String getUrl() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>selfach.Camera.longitude</code>.
	 */
	public void setLongitude(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>selfach.Camera.longitude</code>.
	 */
	public java.lang.String getLongitude() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>selfach.Camera.latitude</code>.
	 */
	public void setLatitude(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>selfach.Camera.latitude</code>.
	 */
	public java.lang.String getLatitude() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>selfach.Camera.vector</code>.
	 */
	public void setVector(java.lang.String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>selfach.Camera.vector</code>.
	 */
	public java.lang.String getVector() {
		return (java.lang.String) getValue(5);
	}

	/**
	 * Setter for <code>selfach.Camera.angle</code>.
	 */
	public void setAngle(java.lang.Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>selfach.Camera.angle</code>.
	 */
	public java.lang.Integer getAngle() {
		return (java.lang.Integer) getValue(6);
	}

	/**
	 * Setter for <code>selfach.Camera.description</code>.
	 */
	public void setDescription(java.lang.String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>selfach.Camera.description</code>.
	 */
	public java.lang.String getDescription() {
		return (java.lang.String) getValue(7);
	}

	/**
	 * Setter for <code>selfach.Camera.working</code>.
	 */
	public void setWorking(java.lang.Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>selfach.Camera.working</code>.
	 */
	public java.lang.Integer getWorking() {
		return (java.lang.Integer) getValue(8);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record9 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row9<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.Integer> fieldsRow() {
		return (org.jooq.Row9) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row9<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.Integer> valuesRow() {
		return (org.jooq.Row9) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return com.selfach.dao.jooq.tables.Camera.CAMERA.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.selfach.dao.jooq.tables.Camera.CAMERA.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return com.selfach.dao.jooq.tables.Camera.CAMERA.URL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return com.selfach.dao.jooq.tables.Camera.CAMERA.LONGITUDE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return com.selfach.dao.jooq.tables.Camera.CAMERA.LATITUDE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field6() {
		return com.selfach.dao.jooq.tables.Camera.CAMERA.VECTOR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field7() {
		return com.selfach.dao.jooq.tables.Camera.CAMERA.ANGLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field8() {
		return com.selfach.dao.jooq.tables.Camera.CAMERA.DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field9() {
		return com.selfach.dao.jooq.tables.Camera.CAMERA.WORKING;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getUrl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getLongitude();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getLatitude();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value6() {
		return getVector();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value7() {
		return getAngle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value8() {
		return getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value9() {
		return getWorking();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CameraRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CameraRecord value2(java.lang.String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CameraRecord value3(java.lang.String value) {
		setUrl(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CameraRecord value4(java.lang.String value) {
		setLongitude(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CameraRecord value5(java.lang.String value) {
		setLatitude(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CameraRecord value6(java.lang.String value) {
		setVector(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CameraRecord value7(java.lang.Integer value) {
		setAngle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CameraRecord value8(java.lang.String value) {
		setDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CameraRecord value9(java.lang.Integer value) {
		setWorking(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CameraRecord values(java.lang.Integer value1, java.lang.String value2, java.lang.String value3, java.lang.String value4, java.lang.String value5, java.lang.String value6, java.lang.Integer value7, java.lang.String value8, java.lang.Integer value9) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached CameraRecord
	 */
	public CameraRecord() {
		super(com.selfach.dao.jooq.tables.Camera.CAMERA);
	}

	/**
	 * Create a detached, initialised CameraRecord
	 */
	public CameraRecord(java.lang.Integer id, java.lang.String name, java.lang.String url, java.lang.String longitude, java.lang.String latitude, java.lang.String vector, java.lang.Integer angle, java.lang.String description, java.lang.Integer working) {
		super(com.selfach.dao.jooq.tables.Camera.CAMERA);

		setValue(0, id);
		setValue(1, name);
		setValue(2, url);
		setValue(3, longitude);
		setValue(4, latitude);
		setValue(5, vector);
		setValue(6, angle);
		setValue(7, description);
		setValue(8, working);
	}
}
