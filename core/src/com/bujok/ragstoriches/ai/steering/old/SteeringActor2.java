package com.bujok.ragstoriches.ai.steering.old;


        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.ai.steer.Steerable;
        import com.badlogic.gdx.ai.steer.SteeringAcceleration;
        import com.badlogic.gdx.ai.steer.SteeringBehavior;
        import com.badlogic.gdx.ai.steer.behaviors.Arrive;
        import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
        import com.badlogic.gdx.ai.steer.behaviors.Seek;
        import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
        import com.badlogic.gdx.ai.utils.Location;
        import com.badlogic.gdx.graphics.Color;
        import com.badlogic.gdx.graphics.g2d.Batch;
        import com.badlogic.gdx.graphics.g2d.TextureRegion;
        import com.badlogic.gdx.math.MathUtils;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.scenes.scene2d.Actor;
        import com.badlogic.gdx.scenes.scene2d.ui.Image;
        import com.badlogic.gdx.utils.Align;
        import com.badlogic.gdx.utils.Array;
        import com.bujok.ragstoriches.ai.utilities.SteeringUtilities;

/** A SteeringActor is a scene2d {@link Actor} implementing the {@link Steerable} interface.
 *
 * @autor davebaol */
public abstract class SteeringActor2 extends Image implements Steerable<Vector2> {

    private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());

    TextureRegion region;

    Vector2 position;  // like scene2d centerX and centerY, but we need a vector to implement Steerable
    Vector2 linearVelocity;
    float angularVelocity;
    float boundingRadius;
    boolean tagged;

    float maxLinearSpeed = 80;
    float maxLinearAcceleration = 10000;
    float maxAngularSpeed = 10000;
    float maxAngularAcceleration = 10000;

    boolean independentFacing;

    SteeringBehavior<Vector2> steeringBehavior;
    private Location<Vector2> target;
    private LinePath<Vector2> path;

    public SteeringActor2 (TextureRegion region) {
        this(region, true);
    }

    public SteeringActor2 (TextureRegion region, boolean independentFacing) {
        super(region);
        this.independentFacing = independentFacing;
        this.region = region;
        this.position = new Vector2();
        this.linearVelocity = new Vector2();
        this.setBounds(0, 0, region.getRegionWidth(), region.getRegionHeight());
        this.boundingRadius = (region.getRegionWidth() + region.getRegionHeight()) / 4f;
        this.setOrigin(region.getRegionWidth() * .5f, region.getRegionHeight() * .5f);
    }

    public TextureRegion getRegion () {
        return region;
    }

    public void setRegion (TextureRegion region) {
        this.region = region;
    }

    @Override
    public Vector2 getPosition () {
        return position;
    }

    @Override
    public float getOrientation () {
        return getRotation() * MathUtils.degreesToRadians;
    }

    @Override
    public void setOrientation (float orientation) {
        setRotation(orientation * MathUtils.radiansToDegrees);
    }

    @Override
    public Vector2 getLinearVelocity () {
        return linearVelocity;
    }

    @Override
    public float getAngularVelocity () {
        return angularVelocity;
    }

    public void setAngularVelocity (float angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    @Override
    public float getBoundingRadius () {
        return boundingRadius;
    }

    @Override
    public boolean isTagged () {
        return tagged;
    }

    @Override
    public void setTagged (boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public Location<Vector2> newLocation () {
        return new Scene2DLocation();
    }

    @Override
    public float vectorToAngle (Vector2 vector) {
        return SteeringUtilities.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector (Vector2 outVector, float angle) {
        return SteeringUtilities.angleToVector(outVector, angle);
    }

    @Override
    public float getMaxLinearSpeed () {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed (float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration () {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration (float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed () {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed (float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration () {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration (float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public float getZeroLinearSpeedThreshold () {
        return 0.001f;
    }

    @Override
    public void setZeroLinearSpeedThreshold (float value) {
        throw new UnsupportedOperationException();
    }

    public boolean isIndependentFacing () {
        return independentFacing;
    }

    public void setIndependentFacing (boolean independentFacing) {
        this.independentFacing = independentFacing;
    }

    public SteeringBehavior<Vector2> getSteeringBehavior () {
        return steeringBehavior;
    }

    public void setSteeringBehavior (SteeringBehavior<Vector2> steeringBehavior) {
        this.steeringBehavior = steeringBehavior;
    }

    @Override
    public void act (float delta) {
        position.set(getX(Align.center), getY(Align.center));
        if (steeringBehavior != null) {

            // Calculate steering acceleration
            steeringBehavior.calculateSteering(steeringOutput);

			/*
			 * Here you might want to add a motor control layer filtering steering accelerations.
			 *
			 * For instance, a car in a driving game has physical constraints on its movement: it cannot turn while stationary; the
			 * faster it moves, the slower it can turn (without going into a skid); it can brake much more quickly than it can
			 * accelerate; and it only moves in the direction it is facing (ignoring power slides).
			 */

            // Apply steering acceleration
            applySteering(steeringOutput, delta);

            wrapAround(position, 1200, 750);
            setPosition(position.x, position.y, Align.center);
        }
        if (position != null && this.target != null && position.dst(this.target.getPosition()) < this.boundingRadius)
        {
            // we have arrived lets notify
            this.notifyArrivalAtTarget();
        }
        super.act(delta);
    }

    protected abstract void notifyArrivalAtTarget();

    // the display area is considered to wrap around from top to bottom
    // and from left to right
    protected static void wrapAround (Vector2 pos, float maxX, float maxY) {
        if (pos.x > maxX) pos.x = 0.0f;

        if (pos.x < 0) pos.x = maxX;

        if (pos.y < 0) pos.y = maxY;

        if (pos.y > maxY) pos.y = 0.0f;
    }

    private void applySteering (SteeringAcceleration<Vector2> steering, float time) {
        // Update position and linear velocity. Velocity is trimmed to maximum speed
        position.mulAdd(linearVelocity, time);
        linearVelocity.mulAdd(steering.linear, time).limit(getMaxLinearSpeed());

        // Update orientation and angular velocity
        if (independentFacing) {
            setRotation(getRotation() + (angularVelocity * time) * MathUtils.radiansToDegrees);
            angularVelocity += steering.angular * time;
        } else {
            // If we haven't got any velocity, then we can do nothing.
            if (!linearVelocity.isZero(getZeroLinearSpeedThreshold())) {
                float newOrientation = vectorToAngle(linearVelocity);
                angularVelocity = (newOrientation - getRotation() * MathUtils.degreesToRadians) * time; // this is superfluous if independentFacing is always true
                setRotation(newOrientation * MathUtils.radiansToDegrees);
            }
        }
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(),
                getRotation());
    }


    public void follow(Steerable<Vector2> target)
    {
        this.path = null;
        this.target = target;
        final Arrive<Vector2> arriveSB = new Arrive<Vector2>(this, target); //
        arriveSB.setTimeToTarget(0.1f) //
                .setArrivalTolerance(0.001f) //
                .setDecelerationRadius(80);
        this.setSteeringBehavior(arriveSB);
    }

    public void moveTo(Vector2 target)
    {
        this.path = null;
        this.target = new Scene2DLocation(target);
        final Seek<Vector2> seekB = new Seek<Vector2>(this, this.target);
        this.setSteeringBehavior(seekB);
        Gdx.app.debug("Steering", "Moving to " + target);
    }

    public void followPath(Array<Vector2> waypoints)
    {
        this.path = new LinePath<Vector2>(waypoints, false);
        this.target = new Scene2DLocation(this.path.getEndPoint());
        final FollowPath<Vector2, LinePath.LinePathParam> pathB = new FollowPath<Vector2, LinePath.LinePathParam>(this, this.path, 30) //
        .setTimeToTarget(0.1f) //
        .setArrivalTolerance(0.001f) //
        .setDecelerationRadius(80);
        pathB.setArriveEnabled(false);
        this.setSteeringBehavior(pathB);
        Gdx.app.debug("Steering", "Moving to " + this.path.getEndPoint());
    }
}