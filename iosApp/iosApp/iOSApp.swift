import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        ComposeApp.KoinKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
