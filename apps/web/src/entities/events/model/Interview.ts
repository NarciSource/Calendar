import { Company } from "./Company";

export class Interview {
    constructor(
        public company: Company,
        public interviewTime: string,
        public position: string,
        public category: string,
        public description: string,
        public interviewDetailId: string
    ) { }

    static empty() {
        return new Interview(new Company("", ""), "", "", "", "", "")
    }

    [key: string]: any;
}
