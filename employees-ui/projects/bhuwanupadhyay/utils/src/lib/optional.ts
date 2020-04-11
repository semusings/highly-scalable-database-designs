export class Optional<T> {

  private val: any;

  private constructor(val: any) {
    this.val = val;
  }

  public static of<T>(val: T) {
    return new Optional<T>(val);
  }

  map<R>(callback: (v: T) => R) {
    if (this.val) {
      this.val = callback(this.val);
    }
    return Optional.of<R>(this.val);
  }

  filter(callback: (v) => boolean) {
    if (this.val && !callback(this.val)) {
      this.val = null;
    }
    return Optional.of<T>(this.val);
  }

  get(): T {
    if (this.val) {
      return this.val;
    } else {
      throw new Error('No value present.');
    }
  }

  orElseGet(callback: () => T): T {
    if (this.val) {
      return this.val;
    } else {
      return callback();
    }
  }

  orElse(otherwise: T) {
    if (this.val) {
      return this.val;
    } else {
      return otherwise;
    }
  }

  orElseThrow(callback: () => Error) {
    if (this.val) {
      return this.val;
    } else {
      return callback();
    }
  }

}
