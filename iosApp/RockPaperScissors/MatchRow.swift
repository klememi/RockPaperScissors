import RPSKit
import SwiftUI

struct MatchRow: View {

	let model: MatchRowComponentModel

	var body: some View {
		VStack(spacing: 10) {
			Text(model.rounds)
				.foregroundColor(model.isLive ? .red : .gray)
				.font(.caption)
				.fontWeight(.bold)
				.fontDesign(.monospaced)

			HStack {
				Participant(model: model.participantA, isLive: model.isLive)

				Text("-")
					.font(.system(.body, design: .monospaced))
					.foregroundColor(model.isLive ? .red : nil)

				Participant(model: model.participantB, isLive: model.isLive)
				.environment(\.layoutDirection, .rightToLeft)
			}
		}
		.padding(8)
	}
}

extension MatchRowComponentModel: Identifiable {}

struct MatchRow_Previews: PreviewProvider {

	static var previews: some View {
		VStack {
			MatchRow(
				model: .init(
					participantA: .init(
						name: "Jana",
						score: "3",
						flag: "🇸🇰",
						isWinner: true
					),
					participantB: .init(
						name: "Petr",
						score: "2",
						flag: "🇨🇿",
						isWinner: false
					),
					rounds: "ROUND 42",
					isLive: false
				)
			)

			MatchRow(
				model: .init(
					participantA: .init(
						name: "Alex",
						score: "0",
						flag: "🇸🇨",
						isWinner: false
					),
					participantB: .init(
						name: "Steve",
						score: "0",
						flag: "🇱🇰",
						isWinner: false
					),
					rounds: "ROUND 999",
					isLive: true
				)
			)
		}
	}
}
