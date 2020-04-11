import {ServerErrorTranslatorPipe} from './server-error-translator.pipe';

describe('ServerErrorTranslatorPipe', () => {
    it('create an instance', () => {
        const pipe = new ServerErrorTranslatorPipe();
        expect(pipe).toBeTruthy();
    });
});
